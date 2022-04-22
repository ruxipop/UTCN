def foodHeuristic(state, problem):
   
   
    "*** Date Initiale***"
    position, foodGrid = state
    distances = [] # lista care va tine minte 
                   # distantele de la position la mancare
    
    "*** Parcurgerea Mancarii ***"
    " Se parcurge mancarea, se tin minte distantele, iar la final" \
    " se caluleaza maximul dintre distante, aceasta fiind euristica "
    for food in foodGrid.asList():
        distances.append(mazeDistance(position, food, problem.startingGameState))
    if len(distances) == 0: return 0
    return max(distances)