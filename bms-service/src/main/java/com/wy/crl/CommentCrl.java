package com.wy.crl;

import com.wy.crl.BaseCrl;
import com.wy.entity.Comment;
import com.wy.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentCrl extends BaseCrl<Comment> {
    @Autowired
    private CommentService commentService;

    @Override
    public CommentService getService() {
        return commentService;
    }
}