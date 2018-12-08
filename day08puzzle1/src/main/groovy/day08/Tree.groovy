package day08

class Tree {

    List<Node> nodes = []
    
    int sumMeta() {
        nodes.sum{it.metaData.sum()}
    }
    
    static Tree fromList(List<Integer> descriptors) {
        Tree result = new Tree()
        Stack<Node> builderStack = new Stack<>()
        Node current = null
        boolean metaIsNext = false
        def i = 0
        while(i<descriptors.size()) {
            int ds = descriptors[i]
            if(current == null) {
                current = new Node(childsCount: ds)
                result.nodes.add(current)
                metaIsNext = true
                ++i
            } else if (metaIsNext) {
                current.metaDataCount=ds
                metaIsNext = false
                ++i
            } else if(current.isChildsRead() && !current.isMetaDataRead()) {
                current.metaData.add(ds)
                ++i
            } else if(current.isChildsRead() && current.isMetaDataRead()) {
                current=builderStack.pop()
            } else if(!current.isChildsRead()) {
                Node child = new Node(childsCount: ds)
                result.nodes<<child
                current.childs<<child
                builderStack.push(current)
                current = child
                metaIsNext = true
                ++i
            }
        }
        return result
    }
    
}
