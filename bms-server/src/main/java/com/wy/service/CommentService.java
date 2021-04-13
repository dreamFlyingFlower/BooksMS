package com.wy.service;

import com.wy.dao.CommentDao;
import com.wy.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commentService")
public class CommentService extends BaseService<Comment> {
    @Autowired
    private CommentDao commentDao;

    @Override
    public CommentDao getDao() {
        return commentDao;
    }
}