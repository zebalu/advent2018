package day07puzzle1

class ParallelInstructionConsumer {
    
    private final Instruction instrution
    private final int[] workersSecondsLeft
    private final String[] workersTask

    ParallelInstructionConsumer(Instruction instruction, int workerCount) {
        this.instrution = instruction
        workersSecondsLeft = new int[workerCount]
        workersTask = new String[workerCount]
    }
    
    int workOnIt() {
        int seconds = 0;
        while(!instrution.isItBuilt()) {
            assignTasks()
            workOnTasks()
            removeFinshedTasks()
            ++seconds
        }
        return seconds;
    }
    
    boolean isAnybodyWorking() {
        int count = 0
        workersSecondsLeft.each { count+=it }
        return count != 0
    }
    
    boolean hasFreeWorker( ) {
        boolean found = false
        workersSecondsLeft.each { found |= it == 0 }
        return found
    }
    
    private int getFirstFreeWorkerId() {
        for(int i=0; i<workersSecondsLeft.size(); ++i) {
            if(workersSecondsLeft[i] == 0) {
                return i;
            }
        }
        throw new NoSuchElementException("there is no free worker")
    }
    
    private void workOnTasks() {
        for(int i=0; i<workersSecondsLeft.size(); ++i) {
            if(workersSecondsLeft[i]>0) {
                workersSecondsLeft[i]--
            }
        }
    }
    
    private void assignTasks() {
        while(hasBuildable() && hasFreeWorker()) {
            int id = getFirstFreeWorkerId()
            InstructionPart task = instrution.instructions[buildables[0]]
            workersSecondsLeft[id] = 60+task.price
            workersTask[id] = task.id
        }
    }
    
    private boolean hasBuildable() {
        !buildables.isEmpty()
    }
    
    private List<String> getBuildables() {
        filterByBeingBuilt(instrution.canBeBuitNext())
    }
    
    private List<String> filterByBeingBuilt(List<String> original) {
        List<String> result = []
        original.each { 
            boolean found = false
            for(int i=0; i<workersTask.size() && !found; ++i) {
                found = workersTask[i] == it
            }
            if(!found) {
                result.add(it)
            }
        }
        return result
    }
    
    private void removeFinshedTasks() {
        workersSecondsLeft.eachWithIndex { v, idx ->
            if(v==0 && workersTask[idx]!=null) {
                String id = workersTask[idx]
                workersTask[idx] = null
                instrution.build(id)
            }
        }
    }
}
