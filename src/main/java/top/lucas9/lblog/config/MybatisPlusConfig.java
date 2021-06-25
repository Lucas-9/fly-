package top.lucas9.lblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *  @EnableTransactionManagement 开启事务支持
 *  @MapperScan 扫描 mapper接口
 */
@Configuration
@EnableTransactionManagement
@MapperScan("top.lucas9.lblog.mapper")
public class MybatisPlusConfig {
    /**
     * @return 返回分页插件对象， 并注入 spring中，id 默认用方法名作为 id
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
