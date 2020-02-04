import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetContactHandler implements RequestHandler<ContactRequest, Contact> {
    private Regions REGION = Regions.US_EAST_2;

    public Contact handleRequest(ContactRequest contactRequest, Context context) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Contact contact = mapper.load(Contact.class, contactRequest.getId());
        if (contact == null) {
            context.getLogger().log("No contact was find with ID: " + contactRequest.getId() + "\n");
            return new Contact();
        }
        return contact;
    }
}
