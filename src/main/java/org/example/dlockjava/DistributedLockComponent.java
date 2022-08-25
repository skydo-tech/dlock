package org.example.dlockjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributedLockComponent {

    final DistributedLockComponentLocked distributedLockComponentLocked;

    Logger log = LoggerFactory.getLogger(DistributedLockComponentLocked.class);

    public DistributedLockComponent(DistributedLockComponentLocked distributedLockComponentLocked) {
        this.distributedLockComponentLocked = distributedLockComponentLocked;
    }

    public void initFunction() {
        try {
            distributedLockComponentLocked.testLock("alpha-key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//@Component
//class LockComponent (
//        private val lockComponentLocked: LockComponentLocked
//        ) {
//
//    private val log = LoggerFactory.getLogger(LockComponent::class.java)
//
//    fun initFunction() {
//        try {
//            val returnType = this.lockComponentLocked.testLock("lock-key", "2", "3")
////            log.info("returnType = $returnType")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//}
//
