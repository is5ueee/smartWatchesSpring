package com.ch.smartTank.Mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Liu Zhe
 * @Description
 * @date 2022/4/21 11:16
 */
@Mapper
@Component
public interface MyMapper {
	Map<String,Object> login(String username,String password);
}
