# Why does this repo exist
- To get an better insight to why the Axis2 broke the JAXWS implementation with new version 1.8.0
- And to prove that it worked on version 1.7.9

## So what is the issue?
- The connections leased from the connection pool are never returned.
- Setting the property `AutoReleaseConnection` to `true` breaks other things and can't be utilized.

## Current Specifications
- SapMachine 11, see https://sap.github.io/SapMachine/ 
- Maven 3.8.1, see https://maven.apache.org/download.cgi
- Apache Ant 1.10.11, see https://ant.apache.org/bindownload.cgi

## But how can I run the things?
- Search for the run.cmd
- If you are not a windows user simply execute the commands from run.cmd in the directory it is located

## More info
- Ticket Number: https://issues.apache.org/jira/browse/AXIS2-6030
- This is the stack trace we extracted
```
java.lang.Thread.State: WAITING
	at java.base@11.0.10/jdk.internal.misc.Unsafe.park(Native Method)
	at java.base@11.0.10/java.util.concurrent.locks.LockSupport.park(LockSupport.java:194)
	at java.base@11.0.10/java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2081)
	at org.apache.http.pool.AbstractConnPool.getPoolEntryBlocking(AbstractConnPool.java:393)
	at org.apache.http.pool.AbstractConnPool.access$300(AbstractConnPool.java:70)
	at org.apache.http.pool.AbstractConnPool$2.get(AbstractConnPool.java:253)
	 - locked org.apache.http.pool.AbstractConnPool$2@27fbc54e
	at org.apache.http.pool.AbstractConnPool$2.get(AbstractConnPool.java:198)
	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.leaseConnection(PoolingHttpClientConnectionManager.java:306)
	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager$1.get(PoolingHttpClientConnectionManager.java:282)
	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:190)
	at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
	at org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89)
	at org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:110)
	at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:72)
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
	at org.apache.axis2.transport.http.impl.httpclient4.RequestImpl.execute(RequestImpl.java:210)
	at org.apache.axis2.transport.http.HTTPSender.send(HTTPSender.java:193)
	at org.apache.axis2.transport.http.AbstractHTTPTransportSender.writeMessageWithCommons(AbstractHTTPTransportSender.java:385)
	at org.apache.axis2.transport.http.AbstractHTTPTransportSender.invoke(AbstractHTTPTransportSender.java:213)
	at org.apache.axis2.engine.AxisEngine.send(AxisEngine.java:431)
	at org.apache.axis2.description.OutInAxisOperationClient.send(OutInAxisOperation.java:399)
	at org.apache.axis2.description.OutInAxisOperationClient.executeImpl(OutInAxisOperation.java:225)
	at org.apache.axis2.client.OperationClient.execute(OperationClient.java:150)
	at org.apache.axis2.jaxws.core.controller.impl.AxisInvocationController.execute(AxisInvocationController.java:579)
	at org.apache.axis2.jaxws.core.controller.impl.AxisInvocationController.doInvoke(AxisInvocationController.java:128)
	at org.apache.axis2.jaxws.core.controller.impl.InvocationControllerImpl.invoke(InvocationControllerImpl.java:93)
	at org.apache.axis2.jaxws.client.proxy.JAXWSProxyHandler.invokeSEIMethod(JAXWSProxyHandler.java:373)
	at org.apache.axis2.jaxws.client.proxy.JAXWSProxyHandler.invoke(JAXWSProxyHandler.java:171)
```