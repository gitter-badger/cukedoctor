package com.github.cukedoctor.api;

import com.github.cukedoctor.api.model.Element;

import java.util.List;

/**
 * Created by pestano on 04/06/15.
 */
public class ScenarioResults {
	List<Element> passedScenarios;
	List<Element> failedScenarios;

	public ScenarioResults(List<Element> passedScenarios, List<Element> failedScenarios) {
		this.passedScenarios = passedScenarios;
		this.failedScenarios = failedScenarios;
	}

	public Integer getNumberOfScenariosPassed() {
		return passedScenarios.size();
	}

	public Integer getNumberOfScenariosFailed() {
		return failedScenarios.size();
	}

	public List<Element> getPassedScenarios() {
		return passedScenarios;
	}

	public List<Element> getFailedScenarios() {
		return failedScenarios;
	}

	public Object getNumberOfScenarios() {
		return getNumberOfScenariosFailed() + getNumberOfScenariosPassed();
	}
}
