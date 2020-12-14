using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using InstagramServer.Models;

namespace InstagramServer.IServices
{
    public interface IUserDataService
    {
        Task<UserData> Add(UserData userData);
        Task<IEnumerable<UserData>> GetAll();
        Task<UserData> GetByUserId(Guid id);
        Task Update(UserData userData);
    }
}