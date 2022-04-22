def recursiveDLS(node, problem, limit, solution, exploredList):


    if problem.isGoalState(node): # cand s-a gasit nodul de goal returneaza solutia
        return solution
    else:
        if limit == 0: # daca limit e 0 iesi din recursivitate
            return False
        else:

            cutoffOccurred = False # nu se ajunge la un capat unde trebuie sa ne intoarcem

            successors = problem.expand(node) # aflam succesrii nodului
            for i in range(0, len(successors)):
                child = (problem, successors[i][0], successors[i][1])

                if child[1] not in exploredList and child[1] != node: # daca nu e radacina si nu e in lista de noduri explorate

                    # prin apelul recursiv se construieste lista de solutii si de noduri explorate
                    result = recursiveDLS(child[1], problem, limit - 1, solution + [child[2]], exploredList + [child[1]])

                    if result == False: # daca nu a gasit solutia
                        cutoffOccurred = True # se face cutoff
                    else:
                        if result != False: # a gasit solutia
                            return result
            if cutoffOccurred: # se iese din functie
                return False

            return False

def depthLimitedSearch(problem, limit):
    return recursiveDLS(problem.getStartState(), problem, limit, [], [])

def iterativeDeepeningSearch(problem):

    " Se face o cautare in adancime pana la adancimea depth "
    " Daca nu gaseste nodul de goal, creste adancimea "
    depth = 0
    while True :
        result = depthLimitedSearch(problem, depth)
        if result != False:
            return result
        depth += 1