def aStarSearch(problem, heuristic=nullHeuristic):


    "*** Date initiale ***"
    solution = [] # lista solutiei
    exploredList = [] # lista nodurilor explorate
    frontier = util.PriorityQueue() # frontiera e o coada de prioritate


    "*** Expandam starea initiala ***"
    successors = problem.expand(problem.getStartState()) # sucesorii
    for i in range(0, len(successors)):
        position = successors[i][0] # pozitia succesorului
        g = successors[i][2] # costul succesorului
        h = heuristic(position, problem) # euristica problemei
        f = g + h # valoarea prioritatii
        node = CustomNode(successors[i][0], successors[i][1], g, (-1, -1)) # (-1, -1) indica faptul ca nodul nu are parinte
        frontier.update(node, f)


    "*** Expandam starile urmatoare ***"
    currentNode = frontier.pop()
    solution = solution + [currentNode.action] # construim solutia

    while not problem.isGoalState(currentNode.state): # cat timp nu a gasit goal-ul

        exploredList.append(currentNode) # se pune nodul curent in lista de noduri explorate

        successors = problem.expand(currentNode.state) # se expandeaza nodul curent
        for i in range(0, len(successors)):
            isInExploredList = False # variabila care indica daca nodul se afla in exploredList
            node = CustomNode(successors[i][0], successors[i][1],
                              successors[i][2], currentNode.getState())  # parintele succesorului este nodul curent

            if node.getState() != currentNode.getParent() and node.getState() != problem.getStartState():  # nu adauga parintele nodului curent (sa nu fie bucla infinita) si nu adauga starea initiala
                for i in range(0, len(exploredList)):
                   if node.state == exploredList[i].state: # verifica daca nodul curent e in exploredList
                        isInExploredList = True

                if not isInExploredList: # daca nu e in exploredList
                    node.action = solution + [node.action] # se actualizeaza solutia
                    g = problem.getCostOfActionSequence(node.action) # se determina costul de la pozitia initiala pana la succesor
                    position = node.state  # se actualizeaza pozitia
                    h = heuristic(position, problem)  # euristica
                    f = g + h  # prioritatea
                    frontier.update(node, f) # se adauga succesorul in frontiera

        isInExploredList = True
        while isInExploredList and not frontier.isEmpty(): # cat timp gaseste un nod din frontiera in exploredList, se face pop
          isInExploredList = False
          currentNode = frontier.pop()
          if currentNode.parent == (-1, -1) : # daca nodul parinte e (-1, -1), se reactualizeaza solutia
              solution = [] + [currentNode.action]
          else :
              solution = currentNode.action
          for i in range(0, len(exploredList)):
            if currentNode.state == exploredList[i].state:
                isInExploredList = True


    "*** Solutia ***"
    solution = currentNode.action
    return solution
