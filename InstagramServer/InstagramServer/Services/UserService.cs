using System;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using InstagramServer.IServices;
using InstagramServer.Models;
using Microsoft.EntityFrameworkCore;

namespace InstagramServer.Services
{
    public class UserService : IUserService
    {
        private readonly InstagramContext _context;

        public UserService(InstagramContext context)
        {
            _context = context;
        }
        
        public async Task<User> Add(User user)
        {
            user.UserId = Guid.NewGuid();

            #region HashPassword

            byte[] salt;
            new RNGCryptoServiceProvider().GetBytes(salt = new byte[16]);
            
            var pbkdf2 = new Rfc2898DeriveBytes(user.Password, salt, 10000);
            byte[] hash = pbkdf2.GetBytes(20);
            
            byte[] hashBytes = new byte[36];
            Array.Copy(salt, 0, hashBytes, 0, 16);
            Array.Copy(hash, 0, hashBytes, 16, 20);
            
            string savedPasswordHash = Convert.ToBase64String(hashBytes);

            user.Password = savedPasswordHash;

            #endregion
            
            var addedUser = _context.Add(user);
            await _context.SaveChangesAsync();
            user.UserId = addedUser.Entity.UserId;

            return user;
        }

        public async Task<User> GetUserById(Guid id)
        {
            return await _context.Users.SingleOrDefaultAsync(x => x.UserId == id);
        }

        public async Task DeleteUser(User user)
        {
            _context.Remove(user);
            await _context.SaveChangesAsync();
        }

        public async Task<User> CheckCredentials(string username, string password)
        {
            var savedPasswordHash = _context.Users.SingleOrDefaultAsync(x => x.Username == username).Result.Password;
            
            byte[] hashBytes = Convert.FromBase64String(savedPasswordHash);
            byte[] salt = new byte[16];
            Array.Copy(hashBytes, 0, salt, 0, 16);
            var pbkdf2 = new Rfc2898DeriveBytes(password, salt, 10000);
            byte[] hash = pbkdf2.GetBytes(20);
            
            for (int i=0; i < 20; i++)
                if (hashBytes[i+16] != hash[i])
                    throw new UnauthorizedAccessException();
            
            return await _context.Users.SingleOrDefaultAsync(x => 
                x.Username == username);
        }
    }
}