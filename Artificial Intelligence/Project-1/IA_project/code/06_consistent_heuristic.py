def cornersHeuristic(state, problem):
    """
    A heuristic for the CornersProblem that you defined.

      state:   The current search state
               (a data structure you chose in your search problem)

      problem: The CornersProblem instance for this layout.

    This function should always return a number that is a lower bound on the
    shortest path from the state to a goal of the problem; i.e.  it should be
    admissible (as well as consistent).
    """

    "*** Date Initiale ***"
    corners = problem.corners # These are the corner coordinates
    walls = problem.walls # These are the walls of the maze, as a Grid (game.py)

    currentState = state[0]
    unvisitedCorners = list(set(corners) - set(state[1])) # lista de corner-uri nevizitate
    h = 0 # euristica


    "*** Euristica ***"
    if currentState not in walls: # daca starea curenta nu e walls
        while len(unvisitedCorners) != 0:

            " Se calculeaza distanta manhattan dintre starea curenta si un corner din unvisitedCorners "
            corner = unvisitedCorners[0]
            mini = util.manhattanDistance(currentState, corner) # distanta minima

            " Se calculeaza distanta dintre starea curenta si celelalte stari, se compara cu distanta minima " \
            " si se actualizeaza daca este necesar "
            for i in range(1, len(unvisitedCorners)):
                dist = util.manhattanDistance(currentState, unvisitedCorners[i])
                if dist < mini:
                  mini = dist
                  corner = unvisitedCorners[i]

            h += mini # la euristica se adauga distanta minima gasita
            currentState = corner # starea curenta devine corner-ul cu distanta cea mai mica

            unvisitedCorners.remove(currentState) # se scoate din unvisitedCorners starea curenta

    return h