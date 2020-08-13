package com.hyr.mapstruct.fixed.vo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {
    private Long id;
    private String username;
    private String password;
    private Integer gender;
    private LocalDate birthday;
    private String createTime;
    private List<UserConfig> config;
	private String test; // 测试字段
	
    @Data
    public static class UserConfig {
        private String field1;
        private Integer field2;
    }
}