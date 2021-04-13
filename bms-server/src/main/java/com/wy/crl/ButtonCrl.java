package com.wy.crl;

import com.wy.entity.Button;
import com.wy.service.ButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("button")
public class ButtonCrl extends BaseCrl<Button> {
    @Autowired
    private ButtonService buttonService;

    @Override
    public ButtonService getService() {
        return buttonService;
    }
}