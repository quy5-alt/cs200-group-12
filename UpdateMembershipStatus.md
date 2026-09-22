# Use Case: Update Membership Status

**Context:** Membership fee processing is performed by Acme Accounting Services. Acme is responsible for suspending members whose membership fees are overdue and reinstating suspended members who have paid the amount owed. Each evening at 9 P.M., Acme updates the appropriate membership records in the ChocAn Data Center. 

**Actors:** Acme Accounting Services, Time Clock 

**Main Success Scenario:**
1. At 9 P.M., Acme Accounting Services sends the membership updates to the ChocAn Data Center.
2. The system receives the updated membership information from Acme Accounting Services.
3. The system identifies the corresponding member records.
4. The system updates each member's membership status based on the information received from Acme Accounting Services.
5. The updated membership status is stored in the member record.


**Extensions:** N/A
