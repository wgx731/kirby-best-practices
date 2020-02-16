package com.github.wgx731.kirby;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
class ApplicationTest {

    @Test
    void applicationTest() {
        Application application = new Application();
        log.info(application.toString());
    }

}