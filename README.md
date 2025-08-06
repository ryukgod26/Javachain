# Javachain 🔗

A complete blockchain implementation in Java featuring cryptocurrency transactions, digital wallets, and proof-of-work mining. This project demonstrates fundamental blockchain concepts including transaction validation, UTXO (Unspent Transaction Output) management, digital signatures, and Merkle trees.

## 🌟 Features

- **Blockchain Architecture**: Complete blockchain with linked blocks and hash validation
- **Cryptocurrency Transactions**: Send and receive digital currency between wallets
- **Digital Wallets**: Secure wallet generation with ECDSA public/private key pairs
- **Proof of Work Mining**: Configurable difficulty mining with nonce calculation
- **Transaction Validation**: Digital signature verification and transaction integrity checks
- **UTXO Management**: Unspent Transaction Output tracking for accurate balance calculation
- **Merkle Trees**: Transaction hashing for block integrity
- **Genesis Block**: Initial block with pre-funded wallet for testing

## 🏗️ Project Structure

```
Javachain/
├── src/javachain/
│   ├── javachain.java        # Main class and blockchain logic
│   ├── Block.java            # Block structure and mining
│   ├── Transaction.java      # Transaction processing and validation
│   ├── Wallet.java           # Wallet management and key generation
│   ├── TransactionInput.java # Transaction input handling
│   ├── TransactionOutput.java# Transaction output management
│   └── StringUtil.java       # Cryptographic utilities
├── lib/
│   ├── bcprov-jdk18on-1.81.jar # Bouncy Castle cryptography library
│   └── gson-2.6.2.jar        # JSON serialization library
└── README.md
```

## 🔧 Technical Implementation

### Core Components

1. **Block Class**: 
   - Contains transaction list, previous block hash, timestamp, and nonce
   - Implements proof-of-work mining algorithm
   - Calculates Merkle root for transaction verification

2. **Transaction Class**:
   - ECDSA digital signature generation and verification
   - Input/output value validation
   - Transaction processing with UTXO management

3. **Wallet Class**:
   - ECDSA key pair generation (prime192v1 curve)
   - Balance calculation from UTXOs
   - Transaction creation and signing

4. **StringUtil Class**:
   - SHA-256 hashing utilities
   - ECDSA signature operations
   - Merkle tree root calculation
   - Base64 encoding for key serialization

### Security Features

- **ECDSA Cryptography**: Elliptic Curve Digital Signature Algorithm for secure transactions
- **SHA-256 Hashing**: Cryptographic hashing for block and transaction integrity
- **Bouncy Castle Provider**: Industry-standard cryptographic library
- **Transaction Validation**: Multi-layer validation including signature verification and balance checks

## 📋 Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Command Line Interface** (PowerShell, Command Prompt, or Terminal)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/ryukgod26/Javachain.git
cd Javachain
```

### 2. Verify Dependencies
The project includes required JAR files in the `lib/` directory:
- `bcprov-jdk18on-1.81.jar` - Bouncy Castle Cryptography Provider
- `gson-2.6.2.jar` - Google JSON library

### 3. Compile the Project
```bash
javac -cp "lib/*" src/javachain/*.java
```

### 4. Run the Blockchain
```bash
java -cp ".;lib/*" src.javachain.javachain
```

**For Linux/Mac users, use colon separator:**
```bash
java -cp ".:lib/*" src.javachain.javachain
```

## 💻 Usage Examples

### Basic Blockchain Demo
The main class demonstrates:
1. Creating wallets with cryptographic key pairs
2. Genesis transaction with initial funding
3. Mining blocks with proof-of-work
4. Transaction processing between wallets
5. Blockchain validation

### Sample Output
```
Creating and Mining genesis Block...
Transaction Successfully added to the Block.
Block Mined ....000a1b2c3d4e5f6789abcdef...
The Balance of Wallet1 is: 100.0
Wallet1 is Sending 10 coins to Wallet2.
Transaction Successfully added to the Block.
Block Mined ....000f1e2d3c4b5a6978563412...
The Balance of Wallet1 is: 90.0
The Balance of Wallet2 is: 10.0
Blockchain is Valid.
The Blockchain is Correct and Working Perfectly.
```

### Configuration Options

**Mining Difficulty**: Adjust in `javachain.java`
```java
public static int difficulty = 3; // Number of leading zeros required
```

**Minimum Transaction Value**: 
```java
public static float minimumTransactionValue = 0.1f;
```

## 🧪 Testing

The project includes comprehensive validation:
- **Hash Integrity**: Verifies block hash calculations
- **Chain Validation**: Ensures proper block linking
- **Transaction Verification**: Validates digital signatures
- **UTXO Consistency**: Checks input/output balance
- **Mining Validation**: Confirms proof-of-work compliance

## 🔍 Understanding the Code

### Key Concepts Implemented

1. **Blockchain Structure**: Linked list of blocks with cryptographic hashes
2. **Mining Process**: Proof-of-work algorithm finding hash with leading zeros
3. **Digital Signatures**: ECDSA signing/verification for transaction authenticity
4. **UTXO Model**: Bitcoin-style unspent transaction output tracking
5. **Merkle Trees**: Efficient transaction verification structure

### Cryptographic Elements

- **Elliptic Curve**: prime192v1 curve for key generation
- **Hash Function**: SHA-256 for all hashing operations
- **Signature Algorithm**: ECDSA with Bouncy Castle provider
- **Encoding**: Base64 for key serialization

## 🛠️ Customization

### Adding New Features
- Modify transaction fees in `Transaction.java`
- Implement different mining algorithms in `Block.java`
- Add transaction types in the main blockchain logic
- Extend wallet functionality for multi-signature support

### Network Simulation
- Create multiple blockchain instances
- Implement consensus mechanisms
- Add peer-to-peer communication

## 📚 Educational Value

This project demonstrates:
- Blockchain fundamentals and cryptocurrency mechanics
- Cryptographic implementations in Java
- Object-oriented design for complex systems
- Security considerations in distributed systems
- Data structures and algorithms in blockchain context

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add comprehensive tests
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🔗 References

- [Bitcoin Whitepaper](https://bitcoin.org/bitcoin.pdf)
- [Bouncy Castle Cryptography](https://www.bouncycastle.org/)
- [ECDSA Algorithm](https://en.wikipedia.org/wiki/Elliptic_Curve_Digital_Signature_Algorithm)
- [Merkle Trees](https://en.wikipedia.org/wiki/Merkle_tree)

## 👨‍💻 Author

**ryukgod26**
- GitHub: [@ryukgod26](https://github.com/ryukgod26)

---
*Built with ❤️ for learning blockchain technology*
