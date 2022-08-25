package org.example.dlockjava;

import org.apache.catalina.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/lock")
@RestController()
public class DistributedLockController {

    final DistributedLockComponent distributedLockComponent;

    Logger log = LoggerFactory.getLogger(DistributedLockComponentLocked.class);

    public DistributedLockController(DistributedLockComponent distributedLockComponent) {
        this.distributedLockComponent = distributedLockComponent;
    }

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    @GetMapping("/test")
    public void test() {
        for(int i = 0; i < 3; i ++) {
            executorService.execute(distributedLockComponent::initFunction);
        }
    }
}


//@RequestMapping("/lock")
//@RestController()
//class MainController (
//        private val component: LockComponent
//) {
//
//    private val executorService = Executors.newFixedThreadPool(3)
//    @GetMapping("/test")
//    fun test() : String {
//        for (i in 1..3) {
//            executorService.execute { component.initFunction() }
//        }
//        return "Check"
//    }
//}