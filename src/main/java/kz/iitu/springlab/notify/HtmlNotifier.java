package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component("html")
@Order(3)
public class HtmlNotifier implements Notifier {

    @PostConstruct
    void init() {
        System.out.println("HTML notifier initialized");
    }

    @Override
    public String send(String message) {
        return "<p>" + HtmlUtils.htmlEscape(message) + "</p>";
    }

    @Override
    public String channel() {
        return "html";
    }
}