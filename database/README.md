For creating a new backup of a database schema, go into the docker container terminal and run: (Replacing the correct values)
mysqldump -uROOT -pPASSWORD --databases DATABASE --skip-comments > /usr/bin/myNewBackup.sql

Then, go into a new ubuntu terminal and run:
docker cp mysql:/usr/bin/myNewBackup.sql /home/roberto/projects/myportfolio/MyPortfolio-BE-v2/database