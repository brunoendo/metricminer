package org.metricminer.view;

import static java.lang.String.format;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.metricminer.infra.dto.KeyValueEntry;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class KeyValueData implements View {

	private String valueName;
	private String keyName;
	private List<KeyValueEntry> entries;
	private final HttpServletResponse response;

	public KeyValueData(HttpServletResponse response) {
		this.response = response;
	}

	public KeyValueData of(List<KeyValueEntry> commitCountByAuthor) {
		this.entries = commitCountByAuthor;
		return this;
	}

	public KeyValueData withKeyName(String keyName) {
		this.keyName = keyName;
		return this;
	}

	public KeyValueData withValueName(String valueName) {
		this.valueName = valueName;
		return this;
	}

	public void serialize() {
		try {
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write("{\"data\":[\n");
			writer.write(format("[\"%s\",\"%s\"]", keyName, valueName));
			for (KeyValueEntry entry : entries) {
				writer.write(format(",\n[\"%s\", %d]", entry.getKey(), entry.getValue()));
			}
			writer.write("]}");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
