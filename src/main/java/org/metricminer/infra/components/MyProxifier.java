package org.metricminer.infra.components;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.proxy.InstanceCreator;
import br.com.caelum.vraptor.proxy.JavassistProxifier;

@ApplicationScoped
@Component
public class MyProxifier extends JavassistProxifier {

	public MyProxifier(InstanceCreator instanceCreator) {
		super(instanceCreator);
	}

}
