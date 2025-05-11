package com.example.TestAppilcation.webhook.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

    @Component
    class webhookinit implements ApplicationRunner {

        @Autowired
        private  webhookService webhookser;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            getWebhookser().startProcess();
        }

        private webhookService getWebhookser() {
            return null;
        }

    }

}
