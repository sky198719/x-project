package com.xxd.h5.service;

import com.xxd.common.bo.Message;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.constants.Constants;
import com.xxd.h5.vo.config.MenuVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * banner接口的调用.
 *
 * @author zhangshengwen
 */
@Service
public class ApiMenuService extends H5BaseService {

    /**
     * 首页banner.
     *
     * @param clientId
     * @param token
     * @return
     */
    public MenuVO getMenus(String clientId, String token, String userAgent) throws CoreException, Exception {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);
        Message menusMessage = null;
        if (StringUtils.isBlank(StringUtils.trim(token))) {
            menusMessage = apiUtil.get(ApiEnum.API_INVESTMENT_HOME_QUICKMENUS, headers);
        } else {
            headers.addHeader("token", token);
            // token 过期的时候返回默认菜单
            try {
                menusMessage = apiUtil.get(ApiEnum.API_INVESTMENT_HOME_USER_QUICKMENUS, headers);
            } catch (CoreException coreException) {
                int code = coreException.getCode();
                if (MessageStatus.TOKEN_EXPIRED.getStatus() == code
                        || MessageStatus.TOKEN_EMPTY.getStatus() == code
                        || MessageStatus.TOKEN_NOTFOUND.getStatus() == code
                        || MessageStatus.TOKEN_MISMATCH.getStatus() == code) {
                    headers.removeHeader("token");
                    menusMessage = apiUtil.get(ApiEnum.API_INVESTMENT_HOME_QUICKMENUS, headers);
                }
            }
        }

        if (menusMessage == null || menusMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            return new MenuVO();
        }

        MenuVO menus = JsonUtil.toObject(menusMessage.getData(), MenuVO.class);
        if (menus == null) {
            return new MenuVO();
        }
        List<MenuVO.Buttons> buttonsList = menus.getQuickHelp();
        if (CollectionUtils.isEmpty(buttonsList)) {
            return new MenuVO();
        }

        for (MenuVO.Buttons buttons : buttonsList) {
            List<MenuVO.Button> buttonList = buttons.getItems();
            if (CollectionUtils.isEmpty(buttonList)) {
                continue;
            }
            for (MenuVO.Button button : buttonList) {
                String menuCode = button.getCode();
                button.setUrl(transformService.generateUrlByMenuCode(menuCode));
            }
        }
        return menus;
    }


}
