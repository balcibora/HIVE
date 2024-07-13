package databasetest1.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@Service
public class RestService 
{

    public String getQuestionTitle (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        return this.getQuestionDocument(questionId, dbFirestore).getString ("name");
    }

    public String createUser (User aUserAccount) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection ("Accounts")
                                                                 .document (aUserAccount.getId())
                                                                 .set (aUserAccount);
        incrementAccountCount();
        return "User successfully created. " + collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUser (String accountId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Map<String, Boolean> friendList = this.getFriendList (accountId);
        String username = this.getUsername (accountId);

        for (String friendId : friendList.keySet())
        {

            this.removeFriend (friendId, username);
        }

        dbFirestore.collection ("Accounts")
                   .document (accountId)
                   .delete();
                                                        
        return "Successfully deleted " + accountId;
    }

    public String[] getAllUserIDs() throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ArrayList<String> users = new ArrayList<String>();
        QuerySnapshot querySnapshot = dbFirestore.collection ("Accounts").get().get();

        for (DocumentSnapshot document : querySnapshot.getDocuments())
        {
            String accountId = document.getId();
            if (accountId.charAt(0) == 'U')
            {
                users.add (accountId);
            }
        }

        return users.toArray (new String[users.size()]);
    }

    public String[] getAllAdminIDs() throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ArrayList<String> admins = new ArrayList<String>();
        QuerySnapshot querySnapshot = dbFirestore.collection ("Accounts").get().get();

        for (DocumentSnapshot document : querySnapshot.getDocuments())
        {
            String accountId = document.getId();
            if (accountId.charAt(0) == 'A')
            {
                admins.add (accountId);
            }
        }

        return admins.toArray (new String[admins.size()]);
    }

    public int getTotalAccountCount() throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Long number = this.getCountDocument ("Count", dbFirestore).getLong ("accountCount");
        return number.intValue();
    }

    public int getTotalQuestionCount() throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Long number = this.getCountDocument ("Count", dbFirestore).getLong ("questionCount");
        return number.intValue();
    }

    public String getUsername (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        return this.getAccountDocument (id, dbFirestore).getString ("username");
    }

    public String getEmail (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        return this.getAccountDocument(id, dbFirestore).getString ("email");
    }

    public int getStreak (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Long number = this.getAccountDocument (id, dbFirestore).getLong ("streak");

        int num = number.intValue();

        return num;
    }

    public int getHighestStreak (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Long number = this.getAccountDocument (id, dbFirestore).getLong ("highestStreak");

        int num = number.intValue();

        return num;
    }

    public String getLastTimeSolved (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String str = this.getAccountDocument (id, dbFirestore).getString ("lastTimeSolved");
        return (str == null ? "" : str);
    }

    public String setLastTimeSolved (String id, String date) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection ("Accounts")
                                                                .document (id)
                                                                .update ("lastTimeSolved", date);

        return "lastTimeSolved successfully set for the account with id " + id + " " + collectionApiFuture.get().getUpdateTime().toString();
    }

    public String getDateOfCreation (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        return this.getAccountDocument (id, dbFirestore).getString ("dateOfCreation");
    }

    private DocumentSnapshot getAccountDocument (String id, Firestore dbFirestore) throws InterruptedException, ExecutionException
    {
        DocumentReference documentReference = dbFirestore.collection ("Accounts")
                                                         .document (id);

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists())
        {
            return document;
        }

        return null;
    }
    
    private DocumentSnapshot getCountDocument (String id, Firestore dbFirestore) throws InterruptedException, ExecutionException
    {
        DocumentReference documentReference = dbFirestore.collection ("Counts")
                                                         .document (id);

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists())
        {
            return document;
        }

        return null;
    }

    private DocumentSnapshot getLastActivityDocument (String id, Firestore dbFirestore) throws InterruptedException, ExecutionException
    {
        DocumentReference documentReference = dbFirestore.collection ("Date")
                                                         .document (id);

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists())
        {
            return document;
        }

        return null;
    }

    public boolean usernameAlreadyExists (String aUsername) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collection = dbFirestore.collection ("Accounts");

        Query query = collection.whereEqualTo ("username", aUsername);
        QuerySnapshot querySnapshot = query.get().get();

        if (querySnapshot.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public String login (String aUsername, String aPassword) throws InterruptedException, ExecutionException
    {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        if(!formattedToday.equals(getLastActivity())) {
            String[] userIDs = getAllUserIDs();
            LocalDate yesterday = today.minusDays(1);
            String formattedYesterday = yesterday.format(formatter);
            for(int i = 0; i < userIDs.length; i++) {
                if(!getLastTimeSolved(userIDs[i]).equals(formattedYesterday)) {
                    resetStreak(userIDs[i]);
                }
            }
            setLastActivity();
        }
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collection = dbFirestore.collection ("Accounts");

        Query query = collection.whereEqualTo ("username", aUsername);
        QuerySnapshot querySnapshot = query.get().get();

        if (!querySnapshot.isEmpty())
        {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

            if (aPassword.equals(document.getString ("password")))
            {
                return document.getId();
            }
        }
        
        return null;
    }

    public boolean changePassword (String userId, String aPassword, String anOldPassword) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String currentPassword = this.getAccountDocument(userId, dbFirestore).getString ("password");
        if (aPassword.equals(currentPassword) || !anOldPassword.equals(currentPassword))
        {
            return false;
        }

        dbFirestore.collection ("Accounts")
                   .document (userId)
                   .update ("password", aPassword);
                   
        return true;
    }
    
    public String resetStreak (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection ("Accounts")
                                                                .document (id)
                                                                .update ("streak", 0);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String increaseStreak (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        int currentStreak = this.getStreak (id);

        if(currentStreak + 1 > this.getHighestStreak(id)) {
            this.increaseHighestStreak(id);
        }

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection ("Accounts")
                                                                .document (id)
                                                                .update ("streak", currentStreak + 1);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String increaseHighestStreak (String id) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        int currentStreak = this.getHighestStreak(id);

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection ("Accounts")
                                                                .document (id)
                                                                .update ("highestStreak", currentStreak + 1);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public Map<String, Boolean> getFriendList (String userId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        @SuppressWarnings("unchecked")
        Map<String, Boolean> friendList = (Map<String, Boolean>)this.getAccountDocument (userId, dbFirestore).get ("friendList");

        return friendList;
    }

    public boolean addFriend (String userId, String friendUsername) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Query query = dbFirestore.collection ("Accounts").whereEqualTo ("username", friendUsername);
        DocumentSnapshot document = query.get().get().getDocuments().get (0);
        String friendId = document.getId();

        @SuppressWarnings("unchecked")
        Map<String, Boolean> friendList1 = (Map<String, Boolean>)this.getAccountDocument (userId, dbFirestore).get ("friendList");

        if (friendList1.containsKey(friendId))
        {              
            return false;
        }

        friendList1.put (friendId, true);

        dbFirestore.collection ("Accounts")
                   .document (userId)
                   .update ("friendList", friendList1);
                   
        return true;
    } 

    public boolean removeFriend (String userId, String friendUsername) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Query query = dbFirestore.collection ("Accounts").whereEqualTo ("username", friendUsername);
        DocumentSnapshot document = query.get().get().getDocuments().get (0);
        String friendId = document.getId();

        @SuppressWarnings("unchecked")
        Map<String, Boolean> friendList1 = (Map<String, Boolean>)this.getAccountDocument (userId, dbFirestore).get ("friendList");

        if (friendList1.containsKey(friendId))
        {
            friendList1.remove (friendId);

            dbFirestore.collection ("Accounts")
                       .document (userId)
                       .update ("friendList", friendList1);

            return true;
        }

        return false;
    }

    public Map<String, String> getSolvedQuestions (String userId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        @SuppressWarnings("unchecked")
        Map<String, String> solvedQuestions = (Map<String, String>)this.getAccountDocument (userId, dbFirestore).get ("solvedQuestions");

        return solvedQuestions;
    }

    public String addToSolvedQuestions (String userId, String questionId) throws InterruptedException, ExecutionException
    {
        
        Firestore dbFirestore = FirestoreClient.getFirestore();

        @SuppressWarnings("unchecked")
        Map<String, String> solvedQuestions = (Map<String, String>)this.getAccountDocument (userId, dbFirestore).get ("solvedQuestions");

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        if(!formattedToday.equals(getLastTimeSolved(userId)) && !solvedQuestions.containsKey(questionId)) {
            increaseStreak(userId);
            setLastTimeSolved(userId, formattedToday);
        }

        solvedQuestions.put (questionId, "solved");

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection ("Accounts")
                                                                .document (userId)
                                                                .update ("solvedQuestions", solvedQuestions);

        return collectionApiFuture.get().getClass().toString();
    }

    public int getNumberOfWrittenQuestions (String adminId) throws InterruptedException, ExecutionException
    {
        return this.getWrittenList (adminId).size();
    }

    public Map<String, String> getWrittenList (String adminId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        @SuppressWarnings("unchecked")
        Map<String, String> writtenList = (Map<String, String>)this.getAccountDocument (adminId, dbFirestore).get ("writtenList");

        return writtenList;
    }

    private void addToWrittenList (String adminId, String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        @SuppressWarnings("unchecked")
        Map<String, String> writtenList = (Map<String, String>)this.getAccountDocument (adminId, dbFirestore).get ("writtenList");

        writtenList.put (questionId, "written");

        dbFirestore.collection ("Accounts")
                   .document (adminId)
                   .update ("writtenList", writtenList);
    }

    public String createOrEditFTBQuestion (FillTheBlankQuestion anFTBQuestion) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(dbFirestore.collection ("Questions").document (anFTBQuestion.getId()).set (anFTBQuestion) != null) {
            addToWrittenList (anFTBQuestion.getAuthor(), anFTBQuestion.getId());
        }
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection ("Questions")
                                                                 .document (anFTBQuestion.getId())
                                                                 .set (anFTBQuestion);
        incrementQuestionCount();
        return "Question successfully created. " + collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String createOrEditFEQuestion (FindErrorQuestion anFEQuestion) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(dbFirestore.collection ("Questions").document (anFEQuestion.getId()).set (anFEQuestion) != null) {
            addToWrittenList (anFEQuestion.getAuthor(), anFEQuestion.getId());
        }
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection ("Questions")
                                                                 .document (anFEQuestion.getId())
                                                                 .set (anFEQuestion);
        incrementQuestionCount();
        return "Question successfully created. " + collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String createOrEditMCQuestion (MultipleChoiceQuestion anMCQuestion) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(dbFirestore.collection ("Questions").document (anMCQuestion.getId()).set (anMCQuestion) != null) {
            addToWrittenList (anMCQuestion.getAuthor(), anMCQuestion.getId());
        }
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection ("Questions")
                                                                 .document (anMCQuestion.getId())
                                                                 .set (anMCQuestion);
        incrementQuestionCount();
        return "Question successfully created. " + collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String createOrEditWCQuestion (WriteCodeQuestion aWCQuestion) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(dbFirestore.collection ("Questions").document (aWCQuestion.getId()).set (aWCQuestion) != null) {
            addToWrittenList (aWCQuestion.getAuthor(), aWCQuestion.getId());
        }
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection ("Questions")
                                                                 .document (aWCQuestion.getId())
                                                                 .set (aWCQuestion);
        incrementQuestionCount();
        return "Question successfully created. " + collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String createOrEditTCQuestion (TraceCodeQuestion aTCQuestion) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(dbFirestore.collection ("Questions").document (aTCQuestion.getId()).set (aTCQuestion) != null) {
            addToWrittenList (aTCQuestion.getAuthor(), aTCQuestion.getId());
        }
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection ("Questions")
                                                                 .document (aTCQuestion.getId())
                                                                 .set (aTCQuestion);
        incrementQuestionCount();
        return "Question successfully created. " + collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String[] getAllQuestionIDs() throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        QuerySnapshot querySnapshot = dbFirestore.collection ("Questions").get().get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        int numberOfQuestions = documents.size();
        String[] questionIDs = new String[numberOfQuestions];

        for (int i = 0; i < numberOfQuestions; i++)
        {
            QueryDocumentSnapshot document = documents.get (i);
            questionIDs[i] = document.getId();
        }

        return questionIDs;
    }

    private DocumentSnapshot getQuestionDocument (String questionId, Firestore dbFirestore) throws InterruptedException, ExecutionException
    {
        DocumentReference documentReference = dbFirestore.collection ("Questions")
                                                         .document (questionId);

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        return future.get();
    }

    public FillTheBlankQuestion getFTBQuestion (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentSnapshot document = this.getQuestionDocument(questionId, dbFirestore);

        if (document.exists())
        {
            return document.toObject (FillTheBlankQuestion.class);
        }
        
        return null;
    }

    public FindErrorQuestion getFEQuestion (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentSnapshot document = this.getQuestionDocument(questionId, dbFirestore);

        if (document.exists())
        {
            return document.toObject (FindErrorQuestion.class);
        }

        return null;
    }

    public MultipleChoiceQuestion getMCQuestion (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentSnapshot document = this.getQuestionDocument(questionId, dbFirestore);

        if (document.exists())
        {
            return document.toObject (MultipleChoiceQuestion.class);
        }

        return null;
    }

    public WriteCodeQuestion getWCQuestion (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentSnapshot document = this.getQuestionDocument(questionId, dbFirestore);

        if (document.exists())
        {
            return document.toObject (WriteCodeQuestion.class);
        }

        return null;
    }

    public TraceCodeQuestion getTCQuestion (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentSnapshot document = this.getQuestionDocument(questionId, dbFirestore);

        if (document.exists())
        {
            return document.toObject (TraceCodeQuestion.class);
        }

        return null;
    }

    public String deleteQuestion (String questionId) throws InterruptedException, ExecutionException
    {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection ("Questions")
                   .document (questionId)
                   .delete();
        
        String adminId = this.getQuestionDocument (questionId, dbFirestore).getString ("author");

        @SuppressWarnings("unchecked")
        Map<String, String> writtenList = (Map<String, String>)this.getAccountDocument (adminId, dbFirestore).get ("writtenList");

        writtenList.remove (questionId);

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection ("Accounts")
                                                                .document (adminId)
                                                                .update ("writtenList", writtenList);

        return "Successfully deleted " + questionId + " " + collectionApiFuture.get().getClass().toString();
    }

    public void setLastActivity() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        LastActivity la = new LastActivity();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        la.setLastActivityDate(formattedToday);
        dbFirestore.collection ("Date").document ("CurrentDate").set(la);
    }

    public String getLastActivity() throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        return this.getLastActivityDocument("CurrentDate", dbFirestore).getString ("lastActivityDate");

    }
    
    private void incrementAccountCount() throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        int currentCount = this.getTotalAccountCount();

        dbFirestore.collection ("Counts").document ("Count").update ("accountCount", currentCount + 1);
    }

    private void incrementQuestionCount() throws InterruptedException, ExecutionException{
        Firestore dbFirestore = FirestoreClient.getFirestore();

        int currentCount = this.getTotalQuestionCount();

        dbFirestore.collection ("Counts").document ("Count").update ("questionCount", currentCount + 1);
    }
}
