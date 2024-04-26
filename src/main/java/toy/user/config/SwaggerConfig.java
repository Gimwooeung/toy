package toy.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(title = "더 커머스 API 명세서",
                description = "더 커머스 서비스 API 명세서",
                version = "v3"))
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("항해 뮤직 API 명세서")
                        .description("더 커머스 서비스 API 명세서")
                        .version("v3"));
    }
}