Distributed prime number searcher.

It connects to a number of remote number checker services, sends them a number to check for primeness and accumulates the results.

To start the checker execute:
```
java -jar <jar_file> <remote_host_1> <remote_port_1> <remote_host_2> <remote_port_2>

```

where <jar_file> is the packaged jar of the application, and <remote_host_N> and <remote_port_N> are the respective remote hosts and ports of the remote services that will check given numbers for primeness.

To start an example prime number checker service execute:
```
java -cp <jar_file>  primenumberservice.PrimeNumberService <port_number>
```

where <port_number> is the local port number on which the service should start.

Prime numbers are persisted in the `primes.dat` file. Should the service crash, best effort would be made to recover the last known prime number and continue from there.
