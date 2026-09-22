**Use Case:** Generate Member Reports

**Context:** Each member who has consulted a ChocAn provider during that week receives a list of services provided to that member, sorted in order of service date. The report, which is also sent as an e-mail attachment

**Actors:** Manager, Time Clock (via Run Weekly Accounting Procedures)

**Main Success Scenario:** 
    1. The system groups the week’s service record by member.
    2. For each member with at least one service provided, the system lists member’s name, card number, street address, city, state and ZIP code, plus the date of service, provider’s name and the service name for each service provided.
    3. The system sends each member report as an email attachment to the respective member.
    
**Extensions:** N/A
