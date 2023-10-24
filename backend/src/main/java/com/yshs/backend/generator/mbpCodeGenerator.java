package com.yshs.backend.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.yshs.backend.mapper.EmployeeMapper;
import com.yshs.backend.service.IEmployeeService;
import jakarta.annotation.Resource;

import javax.sql.DataSource;

public class mbpCodeGenerator {
    @Resource
    DataSource dataSource;

    void contextLoads() {
        FastAutoGenerator
                .create(new DataSourceConfig.Builder(dataSource))
                .packageConfig(builder ->
                        builder.parent("com.yshs.backend")
                )
                .globalConfig(builder ->
                        builder.disableOpenDir()
                                .outputDir("src/main/java")
                                .disableOpenDir()
                )
                .strategyConfig(builder ->
                        builder.entityBuilder()
                                .enableTableFieldAnnotation()
                                .enableChainModel()
                                .enableLombok()
                                .disableSerialVersionUID()

                )
                .strategyConfig(builder ->
                        builder.controllerBuilder()
                                .enableRestStyle()
                )
                .strategyConfig(builder ->
                        builder.serviceBuilder()
                )
                .strategyConfig(builder ->
                        builder.mapperBuilder()
                                .enableMapperAnnotation()
                )
                .execute();
    }
}
