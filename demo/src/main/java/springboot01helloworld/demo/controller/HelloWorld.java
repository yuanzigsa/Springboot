package springboot01helloworld.demo.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloWorld {
    @RequestMapping("hello")
    public String HelloWorld01(){
        return "HelloWorld";
    }
}
