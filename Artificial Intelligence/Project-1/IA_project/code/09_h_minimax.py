
class MinimaxAgent(MultiAgentSearchAgent):
   
    def getAction(self, gameState):
       

        # util.raiseNotDefined()
        #functie care verifica daca se termina apelul lui h_minimax:daca pacman pierde/castiga sau se ajunge
        #la adancimea pana la care se face evaluarea
        def cut_off_test(state,depth):
             if state.isLose() or state.isWin() or depth == self.depth:
                 return True
             else:
                 return  False


        def h_minimax(agent, depth, state):
            if cut_off_test(state,depth):
                return self.evaluationFunction(state)
            #daca nu mai sunt actiuni legale ,se termina
            legalActions=state.getLegalActions(agent)
            if not legalActions:
                return  self.evaluationFunction(state)
            #trebuie sa decidem a cui e randul
            if agent == 0:  #  pt pacman,se determina maximul
                return max(h_minimax(1, depth, state.getNextState(agent, action)) for action in
                           legalActions) #se pune 1 la index deoarece se determina max din agentul urmator
            # e randul pentru fantoma
            else:
                         # a fost ultima fantoma
                if state.getNumAgents()-1 == agent:
                    nextAgent = 0
                    depth += 1  # trebuie sa ia pacman (max) o decizie
                else:
                   nextAgent = agent + 1
                #  se determina minimul
                return min(h_minimax(nextAgent, depth, state.getNextState(agent, action)) for action in
                           legalActions)


        # se incepe de la varf(de la pacman)
        v = float("-inf")
        # vedem ce actiuni poate sa faca pacman
        for action in gameState.getLegalActions(0):
            # pentru fiecare actiune posibila a lui pacman calculam h_minimax
            value = h_minimax(1, 0, gameState.getNextState(0, action))
            # daca minimax da o valoare mai mare decat cea data de noi initial,se schima valoarea initiala
            # si pentru a ceea valoarea ,actiunea este optima
            if value > v or v == float("-inf"):
                v = value
                OptimalAction = action
        # returnam actiunea optima pe care am gasit-o
        return OptimalAction


