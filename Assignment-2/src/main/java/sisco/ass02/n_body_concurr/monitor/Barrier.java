package sisco.ass02.n_body_concurr.monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {

    private Lock mutex;
    private Condition allDone;
    private int nParticipants;
    private int nArrived;

    public Barrier (int nParticipants) {
        this.mutex = new ReentrantLock();
        this.allDone = mutex.newCondition();
        this.nParticipants = nParticipants;
        this.nArrived = 0;
    }

    public void WaitTheyDontLoveYouLikeIDo() throws InterruptedException {
        try {
            mutex.lock();
            nArrived++;
            while (nArrived < nParticipants) {
                allDone.await();                
            }
            if (nArrived == nParticipants) {
                nArrived = 0;
                allDone.signalAll();
            }
        } finally {
            mutex.unlock();
        }
    }


}
