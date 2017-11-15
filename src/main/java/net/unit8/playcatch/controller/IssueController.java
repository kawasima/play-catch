package net.unit8.playcatch.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.bouncr.UserPermissionPrincipal;
import net.unit8.playcatch.MalformedRequestException;
import net.unit8.playcatch.boundary.IssueCreateRequest;
import net.unit8.playcatch.boundary.IssueUpdateRequest;
import net.unit8.playcatch.dao.IssueDao;
import net.unit8.playcatch.dao.JournalDao;
import net.unit8.playcatch.entity.Issue;
import net.unit8.playcatch.entity.Journal;
import net.unit8.playcatch.entity.QueriedIssue;
import org.seasar.doma.In;
import org.seasar.doma.jdbc.SelectOptions;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;

import static enkan.util.BeanBuilder.builder;

public class IssueController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    public List<QueriedIssue> list(UserPermissionPrincipal principal) {
        IssueDao issueDao = daoProvider.getDao(IssueDao.class);
        SelectOptions options = SelectOptions.get();
        return issueDao.selectAll(options);
    }

    @Transactional
    public Issue create(IssueCreateRequest createRequest, UserPermissionPrincipal principal) {
        if (createRequest.hasErrors()) {
            throw new MalformedRequestException(createRequest);
        }
        IssueDao issueDao = daoProvider.getDao(IssueDao.class);
        Issue issue = beansConverter.createFrom(createRequest, Issue.class);
        if (issue.getCreatedAt() == null) {
            issue.setCreatedAt(new Date());
        }
        if (issue.getCreatedBy() == null) {
            issue.setCreatedBy(principal.getName());
        }
        issueDao.insert(issue);
        return issue;
    }

    @Transactional
    public Issue update(Parameters params, IssueUpdateRequest updateRequest, UserPermissionPrincipal principal) {
        if (updateRequest.hasErrors()) {
            throw new MalformedRequestException(updateRequest);
        }
        IssueDao issueDao = daoProvider.getDao(IssueDao.class);
        Issue issue = issueDao.selectById(params.getLong("id"));
        Map<String, Object> req = new HashMap<>();
        beansConverter.copy(updateRequest, req);
        beansConverter.copy(req.entrySet().stream()
                .filter(e -> Objects.nonNull(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                issue);
        issueDao.update(issue);

        JournalDao journalDao = daoProvider.getDao(JournalDao.class);
        journalDao.insert(builder(new Journal())
                .set(Journal::setBallOwner, issue.getBallOwner())
                .set(Journal::setIssueId, issue.getId())
                .set(Journal::setStatus, issue.getStatus())
                .set(Journal::setNote, updateRequest.getNote())
                .set(Journal::setCreatedAt, new Date())
                .set(Journal::setCreatedBy, principal.getName())
                .build());
        return issue;
    }
}
