
    def evaluationFunction(self, currentGameState, action):
      
        # Useful information you can extract from a GameState (pacman.py)
        childGameState = currentGameState.getPacmanNextState(action)
        newPos = childGameState.getPacmanPosition() #pozitia dupa mutare
        newFood = childGameState.getFood() #mancarea ramasa
        newGhostStates = childGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        newFoodList=newFood.asList()
        min_food_distance =-1
        for food in newFoodList:
             distance=util.manhattanDistance(newPos,food)
             if min_food_distance >= distance or min_food_distance==-1:
                 min_food_distance=distance
        distance_to_ghost=1
        for ghost in newGhostStates:
            distance = util.manhattanDistance(newPos, ghost.getPosition())

            if distance_to_ghost >= distance and distance!=0:
                distance_to_ghost = distance
        return childGameState.getScore() +(1 / float(min_food_distance)) + (1 / float(distance_to_ghost))
