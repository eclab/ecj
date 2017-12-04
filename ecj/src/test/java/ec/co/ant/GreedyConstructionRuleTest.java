/*
  Copyright 2017 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/
package ec.co.ant;

import ec.EvolutionState;
import ec.Evolve;
import ec.app.tsp.TSPProblem;
import ec.co.ConstructiveIndividual;
import ec.simple.SimpleEvaluator;
import ec.simple.SimpleEvolutionState;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eric O. Scott
 */
public class GreedyConstructionRuleTest
{
    private final static Parameter BASE = new Parameter("base");
    private final static Parameter PROBLEM_BASE = new Parameter("prob");
    private EvolutionState state;
    private ParameterDatabase params;
    
    public GreedyConstructionRuleTest()
    {
    }
    
    @Before
    public void setUp()
    {
        params = new ParameterDatabase();
        params.set(PROBLEM_BASE.push(TSPProblem.P_FILE), "src/main/resources/ec/app/tsp/berlin52.tsp");
        state = new SimpleEvolutionState();
        state.parameters = params;
        state.output = Evolve.buildOutput();
        state.output.getLog(0).silent = true;
        state.output.getLog(1).silent = true;
        state.output.setThrowsErrors(true);
        state.evaluator = new SimpleEvaluator();
        state.evaluator.p_problem = new TSPProblem();
        state.evaluator.p_problem.setup(state, PROBLEM_BASE);
    }

    @Test
    public void testSetup1()
    {
        final GreedyConstructionRule instance = new GreedyConstructionRule();
        instance.setup(state, BASE);
        assertTrue(instance.isMinimize());
        assertTrue(instance.repOK());
    }

    @Test
    public void testSetup2()
    {
        state.parameters.set(BASE.push(GreedyConstructionRule.P_MINIMIZE), "false");
        final GreedyConstructionRule instance = new GreedyConstructionRule();
        instance.setup(state, BASE);
        assertFalse(instance.isMinimize());
        assertTrue(instance.repOK());
    }

    @Test
    public void testConstructSolution1()
    {
        int startNode = 0;
        final GreedyConstructionRule instance = new GreedyConstructionRule();
        instance.setup(state, BASE);
        final ConstructiveIndividual expResult = new ConstructiveIndividual();
        expResult.setPath(new int[] { 0, 21, 48, 31, 35, 34, 33, 38, 39, 37, 36, 47, 
                                        23, 4, 14, 5, 3, 24, 45, 43, 15, 49, 19, 22, 30,
                                        17, 2, 18, 44, 40, 7, 9, 8, 42, 32, 50, 11, 27,
                                        26, 25, 46, 12, 13, 51, 10, 28, 29, 20, 16, 41, 6, 1});
        final ConstructiveIndividual result = instance.constructSolution(state, new ConstructiveIndividual(), startNode, null);
        assertEquals(expResult, result);
        assertTrue(instance.repOK());
        assertTrue(result.repOK());
    }

    @Test
    public void testConstructSolution2()
    {
        int startNode = 27;
        final GreedyConstructionRule instance = new GreedyConstructionRule();
        instance.setup(state, BASE);
        final ConstructiveIndividual expResult = new ConstructiveIndividual();
        expResult.setPath(new int[] { 27, 26, 25, 46, 12, 13, 51, 10, 50, 11,
                                        24, 3, 5, 4, 14, 23, 47, 37, 39, 36,
                                        38, 35, 34, 33, 43, 45, 15, 49, 19, 22,
                                        30, 17, 21, 0, 48, 31, 44, 18, 40, 7,
                                        9, 8, 42, 32, 2, 16, 20, 29, 28, 41, 6, 1});
        final ConstructiveIndividual result = instance.constructSolution(state, new ConstructiveIndividual(), startNode, null);
        assertEquals(expResult, result);
        assertTrue(instance.repOK());
        assertTrue(result.repOK());
    }

    @Test
    public void testConstructSolution3()
    {
        int startNode = 0;
        state.parameters.set(BASE.push(GreedyConstructionRule.P_MINIMIZE), "false");
        final GreedyConstructionRule instance = new GreedyConstructionRule();
        instance.setup(state, BASE);
        // There are two equivalent greedy solutions in this case.
        final ConstructiveIndividual expResult1 = new ConstructiveIndividual();
        expResult1.setPath(new int[] { 0, 51, 1, 10, 6, 13, 16, 12, 41, 32,
                                        29, 50, 20, 26, 8, 46, 9, 25, 40, 27,
                                        2, 11, 7, 28, 42, 22, 3, 30, 24, 17,
                                        5, 19, 18, 15, 44, 49, 14, 21, 4, 31,
                                        45, 48, 23, 43, 37, 34, 47, 35, 36, 38, 33, 39 });
        final ConstructiveIndividual expResult2 = new ConstructiveIndividual();
        expResult2.setPath(new int[] { 0, 51, 1, 10, 6, 13, 16, 12, 41, 32,
                                        29, 50, 20, 26, 8, 46, 9, 25, 40, 27,
                                        2, 11, 7, 28, 42, 22, 3, 30, 24, 17,
                                        5, 19, 18, 15, 44, 49, 14, 21, 4, 31,
                                        45, 48, 23, 43, 37, 34, 47, 35, 39, 33, 36, 38 });
        final ConstructiveIndividual result = instance.constructSolution(state, new ConstructiveIndividual(), startNode, null);
        assertTrue(result.equals(expResult1) || result.equals(expResult2));
        assertTrue(instance.repOK());
        assertTrue(result.repOK());
    }
    
}
