



class AlphaBetaAgent(MultiAgentSearchAgent):
   
    def getAction(self, gameState):
      
        # functie care verifica daca se termina apelul lui h_minimax:daca pacman pierde/castiga sau se ajunge
        # la adancimea pana la care se face evaluarea
        def terminal_test(state ,depth):
            if state.isLose() or state.isWin() or depth == self.depth:
                return True
            else:
                return False
        #pentru pacman
        def max_value (state,agent,depth,alpha,beta):
            if terminal_test(state, depth):
                 return self.evaluationFunction(state)
            v=float("-inf")

            for action in state.getLegalActions(agent):
                v=max(v,min_value(state.getNextState(agent,action),1,depth,alpha,beta)) #se pune 1 la agent deoarece se ia val
                                                                                        # maxima din urmatorul agent de sub pacman
                if v> beta :
                    return v
                alpha=max(alpha,v)
            return v

        #pentru fantome
        def min_value( state,agent,depth,alpha,beta):

            if terminal_test(state, depth):
                return self.evaluationFunction(state)
            v = float("+inf")
            for action in state.getLegalActions(agent):

                if(agent!=state.getNumAgents()-1):
                    #mai exista fantome
                    v=min(v,min_value(state.getNextState(agent,action),agent+1,depth,alpha,beta))
                else:
                   #pacman
                   #de fiecare data cand pacman ia o decizie depth creste
                   v = min(v, max_value(state.getNextState(agent, action),self.index ,depth+1, alpha, beta))

                if(v< alpha):
                    return v
                beta=min(beta,v)

            return v

        alpha=float("-inf")
        beta = float("+inf")
        # vedem ce actiuni poate sa faca pacman
        for action in gameState.getLegalActions(0):
                 #apelam min_value deoarece mergem pe fantome
                 # daca min_value da o valoare mai mare decat cea data de noi initial,se schima valoarea initiala
                 # si pentru a ceea valoarea ,actiunea este optima
            v=min_value(gameState.getNextState(0,action),1,0,alpha,beta)
            if(v>alpha or alpha==float("-inf")):
                 alpha=v
                 max_a=action
        return max_a
