package ru.oleg.configurator.service.ssh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SshConfig {
    // Network options
    private Integer port;
    private String addressFamily;
    private String listenAddress;

    // Host keys
    private String hostKey;

    // Ciphers and keying
    private String rekeyLimit;

    // Logging
    private String syslogFacility;
    private String logLevel;

    // Authentication
    private String loginGraceTime;
    private String permitRootLogin;
    private Boolean strictModes;
    private Integer maxAuthTries;
    private Integer maxSessions;

    private Boolean pubkeyAuthentication;

    private String authorizedKeysFile;

    private String authorizedPrincipalsFile;

    private String authorizedKeysCommand;
    private String authorizedKeysCommandUser;

    // HostbasedAuthentication
    private Boolean hostbasedAuthentication;
    private Boolean ignoreUserKnownHosts;
    private Boolean ignoreRhosts;

    // Password authentication
    private Boolean passwordAuthentication;
    private Boolean permitEmptyPasswords;
    private Boolean kbdInteractiveAuthentication;

    // Kerberos options
    private Boolean kerberosAuthentication;
    private Boolean kerberosOrLocalPasswd;
    private Boolean kerberosTicketCleanup;
    private Boolean kerberosGetAFSToken;

    // GSSAPI options
    private Boolean gSSAPIAuthentication;
    private Boolean gSSAPICleanupCredentials;
    private Boolean gSSAPIStrictAcceptorCheck;
    private Boolean gSSAPIKeyExchange;

    // PAM
    private Boolean usePAM;

    private Boolean allowAgentForwarding;
    private Boolean allowTcpForwarding;
    private Boolean gatewayPorts;

    // X11 forwarding
    private Boolean x11Forwarding;
    private Integer x11DisplayOffset;
    private Boolean x11UseLocalhost;

    // Miscellaneous
    private Boolean permitTTY;
    private Boolean printMotd;
    private Boolean printLastLog;
    private Boolean tCPKeepAlive;

    private Boolean permitUserEnvironment;
    private String compression;
    private Integer clientAliveInterval;
    private Integer clientAliveCountMax;
    private Boolean useDNS;

    private String pidFile;
    private String maxStartups;
    private Boolean permitTunnel;
    private String chrootDirectory;
    private String versionAddendum;

    private String banner;

    // Environment
    private String acceptEnv;

    // Subsystem
    private String subsystem;
}
