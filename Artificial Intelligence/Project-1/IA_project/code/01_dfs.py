def depthFirstSearch(problem):

    "*** Date initiale ***"

    solution = [] # lista solutiei
    exploredList = [] # lista nodurilor explorate
    frontier = util.Stack() # frontiera e un stack

    isInFrontier = False  # variabila care indica daca nodul e in frontiera
    isInExploredList = False  # variabila care indica daca nodul e in lista nodurilor explorate
    isNodeAddedPrev = True  # variabila care indica daca nodul a fost adaugat ultima data in frontiera


    "*** Expandam starea initiala *** "
    successors = problem.expand(problem.getStartState()) # succesorii

    for i in range(0, len(successors)):
        node = CustomNode(successors[i][0], successors[i][1], # CustomNode(state, action, cost, parent)
                                 successors[i][2], (-1, -1)) # (-1, -1) indica faptul ca nodul nu are parinte
        frontier.push(node)


    "*** Expandam starile urmatoare ***"
    currentNode = frontier.pop() # scoatem primul nod din frontiera

    while not problem.isGoalState(currentNode.getState()) : # cat timp nu a gasit goal-ul

        " Atunci cand s-a atins adancimea maxima, se scod nodurile pana la nodul curent  "
        if not isNodeAddedPrev :

            while exploredList[-1].parent != currentNode.getParent() :
                exploredList.pop(-1)
                solution.pop(-1)

            exploredList.pop(-1)
            solution.pop(-1)

        exploredList.append(currentNode) # se adauga in exploredList nodul curent
        solution.append(currentNode.getAction()) # se adauga in solution actiunea nodului curent


        isNodeAddedPrev = False
        successors = problem.expand(currentNode.getState()) # se expandeaza succesorii nodului curent

        for i in range(0, len(successors)):
            isInExploredList = False
            isInFrontier = False
            node = CustomNode(successors[i][0], successors[i][1],
                                    successors[i][2], currentNode.getState()) # parintele succesorului este nodul curent

            if node.getState() != currentNode.getParent() : # nu adauga parintele nodului curent (sa nu fie bucla infinita)

                if doesStackHaveThisItem(frontier, node) : # verifica daca nodul se afla in frontiera
                        isInFrontier = True

                for i in range(0, len(exploredList)) :
                    if node.state == exploredList[i].state : # verifica daca nodul se afla in lista nodurilor explorate
                        isInExploredList = True

                if not isInExploredList and not isInFrontier : # verifica daca nodul nu e in exploredList si nu e in frontiera
                    frontier.push(node)  # se adauga nodul in frontiera
                    isNodeAddedPrev = True

        currentNode = frontier.pop() # se scoate un nod din frontiera

    "*** Solutia ***"
    solution.append(currentNode.getAction())

    return solution

def doesStackHaveThisItem(stack, item):

    popped = []
    ok = False
    while not stack.isEmpty(): # golim stack-ul
        popped.append(stack.pop())
        if item == popped :
            ok = True

    while len(popped) != 0 : # umplem stack-ul
        stack.push(popped[-1])
        popped.pop(-1)

    return ok