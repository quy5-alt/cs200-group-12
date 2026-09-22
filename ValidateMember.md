**Use Case: Validate Member**

**Context:** Allows a provider to verify whether a member's number is valid and whether the member is currently active before providing a service. This Use Case is included by **"Record Service."**

**Actors:** Provider

**Main Success Scenario:**

1. Provider switches on the terminal and enters their provider number.
2. Provider swipes the member's ChocAn card or manually enters the member's 9-digit member number.
3. System sends the member number to the ChocAn Data Center.
4. System checks the member number against the master member file.
5. System checks the member's status.
6. System displays **"Validated"** on the terminal.

**Extensions:**

**4a. Member number does not exist:** System displays **"Invalid number"** on the terminal, and the Use Case ends.

**5a. Member status is suspended:** System displays **"Member suspended"** on the terminal, and the Use Case ends.
