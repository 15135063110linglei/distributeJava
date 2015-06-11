package com.tenchael.cxf;

import javax.jws.WebService;

@WebService
public interface Business {

	public String echo(String message);

}
