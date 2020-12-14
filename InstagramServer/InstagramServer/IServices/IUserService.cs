using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using InstagramServer.Models;

namespace InstagramServer.IServices
{
    public interface IUserService 
    {
        Task<User> Add(User user);
        Task<User> GetUserById(Guid id);
        Task DeleteUser(User user);
        Task<User> CheckCredentials(string username, string password);
    }
}