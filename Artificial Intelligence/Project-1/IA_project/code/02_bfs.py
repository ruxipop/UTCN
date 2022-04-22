def breadthFirstSearch(problem):


    "*** Date initiale ***"
    solution = [] # lista de solutii
    exploredList = [] # lista de noduri explorate
    frontier = util.Queue() # frontiera reprezentata ca si coada


    "*** Expandam starea initiala *** "
    successors = problem.expand(problem.getStartState()) # succesorii

    for i in range(0, len(successors)):
        node = CustomNode(successors[i][0], successors[i][1], # CustomNode(state, action, cost, parent)
                          successors[i][2], (-1, -1))  # (-1, -1) indica faptul ca nodul nu are parinte
        frontier.push(node)
        exploredList.append(node) # punem in acelasi timp nodurile in lista de stari expandate


    "*** Expandam starile urmatoare ***"
    currentNode = frontier.pop() # scoatem primul nod din frontiera

    while not problem.isGoalState(currentNode.getState()) : # cat timp nu a gasit goal-ul

        successors = problem.expand(currentNode.getState()) # se expandeaza succesorii nodului curent

        for i in range(0, len(successors)):
            isInFrontier = False  # variabila care indica daca nodul e in frontiera
            isInExploredList = False # variabila care indica daca nodul e in exploredList
            node = CustomNode(successors[i][0], successors[i][1],
                              successors[i][2], currentNode.getState()) # parintele succesorului este nodul curent
            if node.getState() != currentNode.getParent() and node.getState() != problem.getStartState():  # nu adauga parintele nodului curent (sa nu fie bucla infinita) si nu adauga starea initiala
                if doesQueueHaveThisItem(frontier, node) : # verifici daca nodul e in frontiera
                    isInFrontier = True
                for i in range(0, len(exploredList)): # verifici daca nodul e in exploredList
                    if node.state == exploredList[i].state:
                        isInExploredList = True

                if not isInExploredList and not isInFrontier : # verifica daca nodul nu e in exploredList si nu e in frontiera
                    frontier.push(node)
                    exploredList.append(node)  # se adauga in acelasi timp si in exploredList

        currentNode = frontier.pop()
        exploredList.append(currentNode) # se adauga nodul curent in exploredList


    "*** Solutia ***"
    list = [] # lista care va contine reverse-ul solutiei
    list.append(exploredList[-1]) # primul element e ultimul element adaugat in exploredList

    while list[-1].getParent() != (-1, -1) : # cat timp nu a ajuns la parintele nodului de start
        for i in range(len(exploredList) - 1, -1, -1):
            if list[-1].getParent() == exploredList[i].getState() : # daca parintele ultimului nod din lista = un nod explorat
                list.append(exploredList[i]) # se adauga nodul in lista
                break

    for i in range(len(list) - 1, -1, -1):
        solution.append(list[i].getAction()) # adaugi la solutie reverse-ul listei

    return solution


def doesQueueHaveThisItem(queue, item):
    popped = []
    ok = False
    while not queue.isEmpty():  # golim coada
        popped.append(queue.pop())
        if item == popped:
            ok = True

    while len(popped) != 0:  # umplem coada
        queue.push(popped[0])
        popped.pop(0)

    return ok