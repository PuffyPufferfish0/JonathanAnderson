### SSH (Secure Shell)

A cryptographic network protocol for operating network services securely over an unsecured network. Commonly used for remote command-line login and execution.

### Key Components

- **Private Key**: The secret key kept on your local machine (e.g., `/home/puffy/labsuser.pem`). _Must have strict permissions (600)._
    
- **Public Key**: The key you share with the server/service (e.g., pasting into GitHub settings).
    
- **`authorized_keys`**: A file on the server (`~/.ssh/authorized_keys`) containing a list of allowed public keys.
    

### Essential Commands

- **Generating a key**: `ssh-keygen -t ed25519 -C "anderson.692@wright.edu"`
    
- **Viewing a public key**: `cat ~/.ssh/id_ed25519.pub`
    
- **Connecting to a server**: `ssh -i /home/puffy/labsuser.pem ubuntu@34.192.142.17`
    

### Git Integration

Used to authenticate `[[Git]]` securely without typing a password every time: `git clone git@github.com:WSU-kduncan/ceg2350-s26-PuffyPufferfish0.git`