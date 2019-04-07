package com.krista.extend.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * KristaMapper
 *
 * @author krista
 * @version V1.0
 * @since 2019/4/6 20:48
 */
public interface KristaMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
