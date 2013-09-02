package org.metricminer.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;

@Resource
public class InfoController {

	@Get("/info/how-it-works") public void howItWorks() {}
	@Get("/info/publications") public void publications() {}
	@Get("/info/authors") public void authors() {}
}
