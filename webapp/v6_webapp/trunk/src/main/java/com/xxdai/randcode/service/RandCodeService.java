package com.xxdai.randcode.service;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zhangyi on 2017/5/16.
 */
@Service("randCodeService")
public class RandCodeService {


    public RandCodeService(){


    }
}
