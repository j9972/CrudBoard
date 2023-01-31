package Crud.CrudBoard.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*
        다른 파일에서 "/upload" 는 없는데 img를 보여주게 하려면,
        detail.html에서 img읠 src를 @{|/upload/@{board.storedFileName}|} 가 가능하게 된다
     */
    private String resourcePath = "/upload/**"; // view 에서 접근할 경로
    private String savePath = "file:///Users/jungsuyoung/springBoot/CrudBoard_img/"; // 실제 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath).addResourceLocations(savePath);
    }
}
