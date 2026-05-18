AY2425_SEM_2_DIT5307 Exam

B2
a.i
Stateful session bean/ reasons:
The operation involves several steps i.e. method calls.
Client-specific state must be retained between method calls during the session.

a.ii.
Singleton session bean/ reasons:
This is a kind of counter which is shared across the whole application and concurrently accessed by clients.

a.iii.
Stateless session bean/ reasons:
The task can be completed with a single method call.
No client-specific states need to be kept between method calls.
(Pooled stateless session beans offer better scalability, which is crucialto the case as requests tend to be frequent.)

b.i.
@Stateful

b.ii.
@Remote
public interface ShoppingCart {
    public void put(String itemID, in Qty);
    public void remove(String itemID, int Qty);
    public List<SelectItem> getAll();
    public void clearAll();
}

b.iii.
Yes.
With remote access, method return values are copies (return by value)
so there is no need to copy the contetns of the list by ourselves.
Information hiding (or encapsulation) is upheld (or side effect cannot occur, etc)
OR
No.
Although with remote access, method return values are copies (return by value)
and there is no need to copy the contents of the list by ourselves, this is a kind
of side effect which should not be depended on (Maybe someday we want to
change the EJB to local access, etc)
Remarks: 1 mark for 'Yes' or 'No' given only if supported with valid reasons

B3
a.
Similarities (any 6 of the keywords/ key phrases):
A message-driven bean's instances retain no conversational state for a specific
client.
All instances of a message-driven bean are equivalent,
allowing the EJB container to assign a message to any message-driven bean
instance. The container can pool these instances to allow streams of messages to be processed concurrently.
A single message-driven bean can process messages from multiple clients.
Differences:
Client components do not locate message-driven beans and 
do not invoke methods directly on them. (A message-driven bean never has a )

b.
Message-driven beans consumes (or receives) messsages asynochronously.
Server resources will not be tied up because of blocking synochronous receives in a server-side component.
Better performance (or scalability, etc) can be achieved.

c.
i. @MessageDriven
ii. "destinationLoopup"
iii. propertyName = "destinationType", propertyValue = "javax.jms.Queue"
iv. implements Messagelistener
v. public void onMessage

B4.
a.
<table>
    <tr>
        <th>Item Description </th>
        <th>Unit Price </th>
        <th>Quantity </th>
    </tr>
    <c:forEach var="item" items="${selectedItems}">
    <tr>
        <td>${item.itemDescription}</td>
        <td>${item.unitPrice}</td>
        <td>${item.quantity}</td>
    </tr>
    </c:forEach>
</table>
