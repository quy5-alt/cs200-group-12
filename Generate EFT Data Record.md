# Use Case: Generate EFT Data Record

**Context:** As part of the weekly accounting procedure, the system generates an Electronic Funds Transfer (EFT) data record for each provider who is to be paid for services provided during the week. For this project, the EFT data is written to a file rather than transferred to a banking system.

**Actors:** Manager, Time Clock (via Run Weekly Accounting Procedures)

**Main Success Scenario:**

1. The system reads the week's file of services provided.
2. The system groups the service records by provider.
3. The system calculates the total amount to be paid to each provider for the week.
4. For each provider to be paid, the system records the provider name, provider number, and amount to be transferred.
5. The system writes the EFT data records to a file.

**Extensions:** N/A
