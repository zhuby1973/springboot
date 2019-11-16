package com.gangquan360.smartadmin.module.email;

import com.baomidou.mybatisplus.plugins.Page;
import com.gangquan360.smartadmin.common.constant.ResponseCodeConst;
import com.gangquan360.smartadmin.common.domain.PageResultDTO;
import com.gangquan360.smartadmin.common.domain.ResponseDTO;
import com.gangquan360.smartadmin.module.email.domain.dto.EmailConfigDTO;
import com.gangquan360.smartadmin.module.email.domain.dto.EmailDTO;
import com.gangquan360.smartadmin.module.email.domain.dto.EmailQueryDTO;
import com.gangquan360.smartadmin.module.email.domain.dto.EmailVO;
import com.gangquan360.smartadmin.module.email.domain.entity.EmailEntity;
import com.gangquan360.smartadmin.module.systemconfig.SystemConfigService;
import com.gangquan360.smartadmin.module.systemconfig.constant.SystemConfigEnum;
import com.gangquan360.smartadmin.util.SmartBeanUtil;
import com.gangquan360.smartadmin.util.SmartPaginationUtil;
import com.gangquan360.smartadmin.util.SmartSendMailUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date 2019-05-13 17:10:16
 * @since JDK1.8
 */
@Service
public class EmailService {

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-13 17:10:16
     */
    public ResponseDTO<PageResultDTO<EmailVO>> queryByPage(EmailQueryDTO queryDTO) {
        Page page = SmartPaginationUtil.convert2PageQueryInfo(queryDTO);
        List<EmailEntity> entities = emailDao.queryByPage(page, queryDTO);
        List<EmailVO> dtoList = SmartBeanUtil.copyList(entities, EmailVO.class);
        page.setRecords(dtoList);
        PageResultDTO<EmailVO> pageResultDTO = SmartPaginationUtil.convert2PageInfoDTO(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-13 17:10:16
     */
    public ResponseDTO<Long> add(EmailDTO addDTO) {
        EmailEntity entity = SmartBeanUtil.copy(addDTO, EmailEntity.class);
        emailDao.insert(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-13 17:10:16
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<Long> update(EmailDTO updateDTO) {
        EmailEntity entity = SmartBeanUtil.copy(updateDTO, EmailEntity.class);
        emailDao.updateById(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-13 17:10:16
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        emailDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-13 17:10:16
     */
    public ResponseDTO<EmailVO> detail(Long id) {
        EmailEntity entity = emailDao.selectById(id);
        EmailVO dto = SmartBeanUtil.copy(entity, EmailVO.class);
        return ResponseDTO.succData(dto);
    }

    /**
     * 发送某个已创建的邮件
     *
     * @param id
     * @return
     */
    public ResponseDTO<String> send(Long id) {
        EmailEntity entity = emailDao.selectById(id);
        EmailConfigDTO emailConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.EMAIL_CONFIG.name(), EmailConfigDTO.class);
        String toEmails = entity.getToEmails();
        if (StringUtils.isEmpty(toEmails)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "收件人信息为空");
        }
        String[] emails = toEmails.split(";");
        SmartSendMailUtil.sendMail(emailConfig.getUsername(), emailConfig.getPassword(), emailConfig.getUsername(), emails, "", emailConfig.getSmtpHost(), entity.getTitle(), entity.getContent());
        entity.setSendStatus(EmailSendStatusEnum.SEND.getType());
        emailDao.updateById(entity);
        return ResponseDTO.succ();
    }

}
