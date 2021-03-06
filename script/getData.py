#!/usr/bin/env python3
import multiprocessing as mp
import time, os

class Daemon(mp.Process):
    def __init__(self, tq, rq):
        mp.Process.__init__(self)
        self.tq = tq
        self.rq = rq
    def run(self):
        while True:
            nt = self.tq.get()
            if nt is None:
                self.tq.task_done()
                break
            ans = nt()
            self.tq.task_done()
            self.rq.put(ans)
        return

class MemTest(object):
    def __call__(self):
        temp = os.popen('cat /proc/meminfo').readlines()
        timestamp = time.ctime()
        return ('mem', timestamp, temp)

class CpuTest(object):
    def __call__(self):
        temp = os.popen('cat /proc/stat').readlines()
        timestamp = time.ctime()
        return ('cpu', timestamp, temp)

class Task(mp.Process):
    def __init__(self, tq, st):
        mp.Process.__init__(self)
        self.tq = tq
        self.st = st
    def run(self):
        while True:
            time.sleep(self.st)
            self.tq.put(MemTest())
            self.tq.put(CpuTest())

class DataStore(mp.Process):
    def __init__(self, rq, f):
        mp.Process.__init__(self)
        self.rq = rq
        self.f = f
    def run(self):
        while True:
            flags, timestamp, data = self.rq.get()
            fname = self.f[flags]
            fname.write('==>' + timestamp + '\n')
            fname.writelines(data)
            fname.flush()

if __name__ == '__main__':
    tasksQueue = mp.JoinableQueue()
    resultsQueue = mp.Queue()

    f1 = open('memstate', 'w')
    f2 = open('cpustate', 'w')
    f = {'mem':f1, 'cpu':f2}

    num_daemons = mp.cpu_count()
    daemons = [Daemon(tasksQueue, resultsQueue) for i in range(num_daemons)]
    for w in daemons:
        w.start()

    #Change time. st='sleep time', rt='run time'
    st = 1; rt = 10
    taskproc = Task(tasksQueue, st)
    taskproc.start()
    datastore = DataStore(resultsQueue, f)
    datastore.start()

    time.sleep(rt)
    taskproc.terminate()
    taskproc.join()
    for i in range(num_daemons):
        tasksQueue.put(None)
    tasksQueue.join()
    datastore.terminate()
    datastore.join()
    for k in f:
        f[k].close()
